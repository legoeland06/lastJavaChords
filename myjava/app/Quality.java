package myjava.app;

/**
 * @author ericbruneau
 *
 */
public class Quality {

	private String qualityName;


	/**
	 * Constructeur vide
	 */
	public Quality() {
		super();
		this.setQualityName("");
	}

	/**
	 * @param qualityName
	 */
	public Quality(String qualityName) {
		super();
		this.qualityName = qualityName;
	}

	/**
	 * @return qualityName
	 */
	public String getQualityName() {
		return qualityName;
	}

	/**
	 * @param qualityName
	 */
	public void setQualityName(String qualityName) {
		this.qualityName = qualityName;
	}

	@Override
	public String toString() {
		return qualityName;
	}

}
