package myjava.app;

import java.util.*;

public class Quality {

	private String qualityName;

	private static final String[][] _qualitiesStrings = { { "ot", ":" }, { "5", "0:7:12:19:" }, { "no5", "0:4:12:16:" },
			{ "omit5", "0:4:12:16:" }, { "m(no5)", "0:3:12:15:" }, { "m(omit5)", "0:3:12:15:" }, { " ", "0:4:7:12:" },
			{ "maj", "0:4:7:12:" }, { "m", "0:3:7:12:" }, { "min", "0:3:7:12:" }, { "-", "0:3:7:12:" },
			{ "dim", "0:3:6:12:" }, { "(b5)", "0:4:6:12:" }, { "aug", "0:4:8:12:" }, { "sus2", "0:2:7:12:" },
			{ "sus4", "0:5:7:12:" }, { "sus", "0:5:7:12:" }, { "6", "0:4:7:9:" }, { "7", "0:4:7:10:" },
			{ "7-5", "0:4:6:10:" }, { "7b5", "0:4:6:10:" }, { "7+5", "0:4:8:10:" }, { "7#5", "0:4:8:10:" },
			{ "7sus4", "0:5:7:10:" }, { "m6", "0:3:7:9:" }, { "m7", "0:3:7:10:" }, { "m7-5", "0:3:6:10:" },
			{ "m7b5", "0:3:6:10:" }, { "m7+5", "0:3:8:10:" }, { "m7#5", "0:3:8:10:" }, { "dim6", "0:3:6:8:" },
			{ "dim7", "0:3:6:9:" }, { "7alt", "0:3:6:9:" }, { "M7", "0:4:7:11:" }, { "maj7", "0:4:7:11:" },
			{ "M7+5", "0:4:8:11:" }, { "mM7", "0:3:7:11:" }, { "add4", "0:4:5:7:" }, { "Madd4", "0:4:5:7:" },
			{ "madd4", "0:3:5:7:" }, { "add9", "0:4:7:14:" }, { "Madd9", "0:4:7:14:" }, { "madd9", "0:3:7:14:" },
			{ "sus4add9", "0:5:7:14:" }, { "sus4add2", "0:2:5:7:" }, { "2", "0:4:7:14:" }, { "add11", "0:4:7:17:" },
			{ "m11", "0:3:7:17:" }, { "4", "0:4:7:17:" }, { "m69", "0:3:7:9:14:" }, { "69", "0:4:7:9:14:" },
			{ "9", "0:4:7:10:14:" }, { "m9", "0:3:7:10:14:" }, { "M9", "0:4:7:11:14:" }, { "maj9", "0:4:7:11:14:" },
			{ "9sus4", "0:5:7:10:14:" }, { "7-9", "0:4:7:10:13:" }, { "7b9", "0:4:7:10:13:" },
			{ "7+9", "0:4:7:10:15:" }, { "7#9", "0:4:7:10:15:" }, { "9-5", "0:4:6:10:14:" }, { "9b5", "0:4:6:10:14:" },
			{ "9+5", "0:4:8:10:14:" }, { "9#5", "0:4:8:10:14:" }, { "7#9b5", "0:4:6:10:15:" },
			{ "7#9#5", "0:4:8:10:15:" }, { "m7b9b5", "0:3:6:10:13:" }, { "7b9b5", "0:4:6:10:13:" },
			{ "7b9#5", "0:4:8:10:13:" }, { "11", "0:7:10:14:17:" }, { "7+11", "0:4:7:10:18:" },
			{ "7#11", "0:4:7:10:18:" }, { "M7+11", "0:4:7:11:18:" }, { "M7#11", "0:4:7:11:18:" },
			{ "7b9#9", "0:4:7:10:13:15:" }, { "7b9#11", "0:4:7:10:13:18:" }, { "7#9#11", "0:4:7:10:15:18:" },
			{ "7-13", "0:4:7:10:20:" }, { "7b13", "0:4:7:10:20:" }, { "m7add11", "0:3:7:10:17:" },
			{ "M7add11", "0:4:7:11:17:" }, { "mM7add11", "0:3:7:11:17:" }, { "7b9b13", "0:4:7:10:13:17:20" },
			{ "9+11", "0:4:7:10:14:18:" }, { "9#11", "0:4:7:10:14:18:" }, { "13", "0:4:7:10:14:21:" },
			{ "13-9", "0:4:7:10:13:21:" }, { "13b9", "0:4:7:10:13:21:" }, { "13+9", "0:4:7:10:15:21:" },
			{ "13#9", "0:4:7:10:15:21:" }, { "13+11", "0:4:7:10:18:21:" }, { "13#11", "0:4:7:10:18:21:" },
			{ "M7add13", "0:4:7:9:11:14:" } };

	public static List<Integer> explodeChord(List<Integer> lis) {
		List<Integer> receptacle = new ArrayList<>();

		if (lis.size() < 5) {
			for (Integer item : lis) {
				receptacle.add(item);
				receptacle.add(item + 12);
			}
			receptacle.sort(Comparator.naturalOrder());
			Application.prtln(receptacle.toString());
			return receptacle;

		} else
			Application.prtln(lis.toString());
		return lis;
	}

	public Quality() {
		super();
		this.setQualityName("");
	}

	public Quality(String qualityName) {
		super();
		this.qualityName = qualityName;
	}

	public String getQualityName() {
		return qualityName;
	}

	public void setQualityName(String qualityName) {
		this.qualityName = qualityName;
	}

	public static List<String> listeQualitiesS() {
		List<String> receptacleQuality = new ArrayList<>();
		for (String[] quali : _qualitiesStrings) {
			receptacleQuality.add(quali[0]);
		}
		return receptacleQuality;
	}

	public static List<String> listeQualitiesQ() {
		List<String> receptacle = new ArrayList<>();
		for (String[] quali : _qualitiesStrings) {
			receptacle.add(quali[0]);
		}
		return receptacle;
	}

	public static List<String> listeQualitiesT() {
		List<String> receptacle = new ArrayList<>();
		for (String[] quali : _qualitiesStrings) {
			receptacle.add(quali[1]);
		}
		return receptacle;
	}

	@Override
	public String toString() {
		return qualityName;
	}

}
