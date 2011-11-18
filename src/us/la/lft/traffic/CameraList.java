package us.la.lft.traffic;

import java.util.ArrayList;

public class CameraList extends ArrayList<CameraValueObject> {
	private static final long serialVersionUID = -4158264483621151016L;
	
	public CameraList() {
		super();
		
		add(30195990, -92075210, "Ambassador Caffery @ Congress", "151");
		add(30188420, -92075080, "Ambassador Caffery @ Curran", "210");
		add(30219150, -92075861, "Ambassador Caffery @ Eraste Landry", "148");
		add(30202460, -92075210, "Ambassador Caffery @ Guilbeau", "150");
		add(30249500, -92066480, "Ambassador Caffery @ I-10 WB Ramp", "185");
		add(30156480, -92048430, "Ambassador Caffery @ Kaliste Saloom", "113");
		add(30181320, -92074970, "Ambassador Caffery @ Ridge", "152");
		add(30171210, -92066260, "Ambassador Caffery @ Robley", "153");
		add(30159760, -92052290, "Ambassador Caffery @ Settlers Trace", "213");
		add(30145790, -92032440, "Ambassador Caffery @ Verot School", "188");
		add(30243200, -92066220, "Ambassador Caffery @ Willow", "186");
		
		add(30227120, -92060770, "Bertrand @ Banks", "66");
		add(30214890, -92046910, "Bertrand @ Congress", "77");
		add(30222320, -92052190, "Bertrand @ Dulles Billeaud", "191");
		
		add(30140820, -91945560, "Evangeline Thruway @ Albertson Pkwy", "801");
		add(30099260, -91941550, "Evangeline Thruway @ Hwy 92", "802");
		add(30224550, -92010280, "Evangeline Thruway @ Johnston (SB)", "12");
		add(30200770, -91999440, "Evangeline Thruway @ Kaliste Saloom", "170");
		add(30224920, -92009360, "Evangeline Thruway @ Louisiana", "11");
		add(30153010, -91959420, "Evangeline Thruway @ Morgan", "800");
		add(30232980, -92012770, "Evangeline Thruway @ Mudd (NB)", "38");
		add(30233190, -92014080, "Evangeline Thruway @ Mudd (SB)", "41");
		add(30217690, -92004870, "Evangeline Thruway @ Pinhook", "33");
		add(30231130, -92013800, "Evangeline Thruway @ Simcoe (SB)", "6");
		add(30171190, -91980040, "Evangeline Thruway @ Southpark", "175");
		add(30209160, -91999960, "Evangeline Thruway @ University/Surrey", "32");
		add(30182820, -91989640, "Evangeline Thruway @ Verot School", "173");
		add(30244530, -92011760, "Evangeline Thruway @ Willow (NB)", "39");
		add(30244750, -92013390, "Evangeline Thruway @ Willow (SB)", "40");
		
		add(30178090, -92074520, "Johnston @ Ambassador Caffery", "31");
		add(30207250, -92038300, "Johnston @ College", "21");
		add(30166240, -92083150, "Johnston @ Duhon", "199");
		add(30193690, -92058640, "Johnston @ Guilbeau/Camelia", "28");
		add(30212310, -92025410, "Johnston @ Lewis", "19");
		add(30181340, -92071300, "Johnston @ Ridge", "30");
		add(30217280, -92019080, "Johnston @ University", "17");
		add(30203390, -92048950, "Johnston @ Vital/Doucet", "23");
		
		add(30167350, -92036240, "Kaliste Saloom @ Camelia", "205");
		add(30172900, -92029740, "Kaliste Saloom @ Rue Louis XIV", "115");
		
		add(30295940, -92024530, "I-49 @ Gloria Switch", "207");
		
		add(30234540, -92002330, "Louisiana @ Carmel", "9");
		add(30272370, -91992550, "Louisiana @ Pont Des Mouton", "202");
		add(30245510, -92000410, "Louisiana @ Willow", "131");
		
		add(30233390, -91992640, "Pinhook @ Carmel", "134");
		add(30188590, -92014120, "Pinhook @ Kaliste Saloom", "79");
		add(30160630, -91991100, "Pinhook @ Southpark", "174");
		add(30223600, -91998200, "Pinhook @ Surrey", "45");
		add(30210510, -92009720, "Pinhook @ University", "47");
		add(30176740, -92002420, "Pinhook @ Verot School", "112");
		
		add(30251690, -92036890, "University @ I-10 WB", "140");
		add(30275910, -92049440, "University @ Pont Des Mouton", "204");
		add(30244510, -92032720, "University @ Willow", "141");
		
		add(30160450, -92022770, "Verot School @ Camellia", "206");
		
		add(30244020, -91988940, "Willow @ Teurlings", "133");
	}
	
	public void add(int latitude, int longitude, String description, String image) {
		add(new CameraValueObject(latitude, longitude, description, image));
	}
}
