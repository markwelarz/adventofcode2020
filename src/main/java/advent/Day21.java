package advent;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.google.common.io.CharSource;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day21
{
	public long part1(CharSource input) throws IOException
	{
		List<Food> allFoods = input.lines()
						.map(v -> parseLine(v))
						.collect(Collectors.toList());

		Map<String, PossibleAllergens> inferredIngredients = inferAllergensIngredients(allFoods);

		// gather set of ingredients that have allergens
		Set<String> ingredientsHavingAllergens = inferredIngredients.values().stream()
						.flatMap(v -> v.possibleIngredients().stream())
						.collect(Collectors.toSet());

		// gather all ingredients
		Set<String> uniqueIngredients = allFoods.stream()
						.flatMap(v -> v.ingredients().stream())
						.collect(Collectors.toSet());

		Set<String> ingredientsWithoutAllergens = Sets.difference(uniqueIngredients, ingredientsHavingAllergens).immutableCopy();
		System.out.println("ingredients not having any allergens " + ingredientsWithoutAllergens);

		// count how many occurences in the input data of the ingredients that have no allergens
		long counts = allFoods.stream()
						.map(v -> Sets.intersection(v.ingredients(), ingredientsWithoutAllergens).size())
						.mapToLong(Integer::longValue).sum();

		return counts;
	}

	public String part2(CharSource input) throws IOException
	{
		List<Food> allFoods = input.lines()
						.map(this::parseLine)
						.collect(Collectors.toList());

		Map<String, PossibleAllergens> inferredIngredients = inferAllergensIngredients(allFoods);

		Comparator<PossibleAllergens> allergenOrder = Comparator.comparing(PossibleAllergens::allergen);

		String dangerousIngredientList = inferredIngredients.values().stream()
						.sorted(allergenOrder)
						.map(v -> Iterables.getOnlyElement(v.possibleIngredients()))
						.collect(Collectors.joining(","));

		return dangerousIngredientList;
	}

	Map<String, PossibleAllergens> inferAllergensIngredients(List<Food> allFoods)
	{
		Map<String, PossibleAllergens> possibles = mapAllergensToIngredients(allFoods);

		boolean didRemove;
		do
		{
			didRemove = false;

			printProgress(possibles);

			// find any allergens where all but 1 ingredient has eliminated, then eliminate it from the other allergens
			List<PossibleAllergens> uniqueMatches = onePossibleIngredient(possibles.values());
			for (PossibleAllergens thisAllergen : uniqueMatches)
			{
				var allExceptThisAllergen = possibles.values().stream()
								.filter(v -> v != thisAllergen)
								.collect(Collectors.toList());

				didRemove |= removeIngredientFromAll(allExceptThisAllergen, Iterables.getOnlyElement(thisAllergen.possibleIngredients()));
			}
		}
		while (didRemove);

		return possibles;
	}

	void printProgress(Map<String, PossibleAllergens> possibles)
	{
		for (var pe : possibles.entrySet())
		{
			System.out.println(pe.getKey() + " " + pe.getValue().possibleIngredients().size());
		}
		System.out.println("==============================");
	}

	List<PossibleAllergens> onePossibleIngredient(Collection<PossibleAllergens> possibles)
	{
		return possibles.stream()
						.filter(v -> v.possibleIngredients().size() == 1)
						.collect(Collectors.toList());
	}

	boolean removeIngredientFromAll(List<PossibleAllergens> possibles, String ingredient)
	{
		boolean removed = false;

		for (PossibleAllergens pe : possibles)
		{
			removed |= pe.possibleIngredients().remove(ingredient);
		}

		return removed;
	}

	Map<String, PossibleAllergens> mapAllergensToIngredients(List<Food> foods)
	{
		Map<String, PossibleAllergens> possibleAllergens = new HashMap<>();

		for (Food food : foods)
		{
			for (String allergen : food.allergens())
			{
				possibleAllergens.merge(allergen, new PossibleAllergens(allergen, Sets.newHashSet(food.ingredients())), this::mergeIngredients);
			}
		}

		return possibleAllergens;
	}

	PossibleAllergens mergeIngredients(PossibleAllergens allergens1, PossibleAllergens allergens2)
	{
		assert allergens1.allergen().equals(allergens2.allergen());
		return new PossibleAllergens(allergens1.allergen(),
						Sets.newHashSet(Sets.intersection(allergens1.possibleIngredients(), allergens2.possibleIngredients())));
	}

	Food parseLine(String line)
	{
		String ingredientText = StringUtils.substringBefore(line, "(contains");
		List<String> ingredients = Splitter.on(" ").trimResults().omitEmptyStrings().splitToList(ingredientText);

		String allergensText = StringUtils.substringBetween(line, "(contains", ")");
		List<String> allergens = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(allergensText);

		return new Food(ImmutableSet.copyOf(allergens), ImmutableSet.copyOf(ingredients));
	}

	record PossibleAllergens(String allergen, Set<String>possibleIngredients)
	{
	}

	record Food(Set<String>allergens, Set<String>ingredients)
	{
	}
}
