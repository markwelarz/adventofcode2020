package advent.support;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PassportEntry
{
	@NotNull
	@Min(1920)
	@Max(2002)
	private Integer byr;
	@NotNull
	@Min(2010)
	@Max(2020)
	private Integer iyr;
	@NotNull
	@Min(2020)
	@Max(2030)
	private Integer eyr;
	@NotNull
	@Height
	private String hgt;
	@NotNull
	@Pattern(regexp = "#[a-f0-9]{6}")
	private String hcl;
	@NotNull
	private EyeColour ecl;
	@NotNull
	@Length(min = 9, max = 9)
	private String pid;
	private String cid;

	public Integer getByr()
	{
		return byr;
	}

	public void setByr(Integer byr)
	{
		this.byr = byr;
	}

	public Integer getIyr()
	{
		return iyr;
	}

	public void setIyr(Integer iyr)
	{
		this.iyr = iyr;
	}

	public Integer getEyr()
	{
		return eyr;
	}

	public void setEyr(Integer eyr)
	{
		this.eyr = eyr;
	}

	public String getHgt()
	{
		return hgt;
	}

	public void setHgt(String hgt)
	{
		this.hgt = hgt;
	}

	public String getHcl()
	{
		return hcl;
	}

	public void setHcl(String hcl)
	{
		this.hcl = hcl;
	}

	public EyeColour getEcl()
	{
		return ecl;
	}

	public void setEcl(EyeColour ecl)
	{
		this.ecl = ecl;
	}

	public String getPid()
	{
		return pid;
	}

	public void setPid(String pid)
	{
		this.pid = pid;
	}

	public String getCid()
	{
		return cid;
	}

	public void setCid(String cid)
	{
		this.cid = cid;
	}
}
