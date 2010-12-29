package us.la.lft.traffic;

import java.util.ArrayList;

public class CameraList extends ArrayList<CameraValueObject> {
	private static final long serialVersionUID = -4158264483621151016L;
	
	public CameraList() {
		super();
		
		add(30195990, -92075210, "Ambassador Caffery @ Congress", "CafferyCongress");
		add(30188420, -92075080, "Ambassador Caffery @ Curran", "CafCurran");
		add(30219150, -92075861, "Ambassador Caffery @ Eraste Landry", "CafferyELandry");
		add(30202460, -92075210, "Ambassador Caffery @ Guilbeau", "CafGuilbeau");
		add(30249500, -92066480, "Ambassador Caffery @ I-10 WB Ramp", "CafferyI10Ramp");
		add(30156480, -92048430, "Ambassador Caffery @ Kaliste Saloom", "CafferyKSaloom");
		add(30181320, -92074970, "Ambassador Caffery @ Ridge", "CafferyRidge");
		add(30171210, -92066260, "Ambassador Caffery @ Robley", "CafRobley");
		add(30159760, -92052290, "Ambassador Caffery @ Settlers Trace", "AmbSett");
		add(30145790, -92032440, "Ambassador Caffery @ Verot School", "CaffVerot");
		add(30243200, -92066220, "Ambassador Caffery @ Willow", "AmbWillow");
		
		add(30227120, -92060770, "Bertrand @ Banks", "BertBanks");
		add(30214890, -92046910, "Bertrand @ Congress", "BertrandCongress");
		add(30222320, -92052190, "Bertrand @ Dulles Billeaud", "BertDullesBilleau");
		
		add(30140820, -91945560, "Evangeline Thruway @ Albertson Pkwy", "ThwyAlbertsons");
		add(30153010, -91959420, "Evangeline Thruway @ Morgan", "ThwyMorgan");
		add(30099260, -91941550, "Evangeline Thruway @ Hwy 92", "ThwyHwy92");
		add(30224550, -92010280, "Evangeline Thruway @ Johnston (SB)", "SBThwyJohn");
		add(30200770, -91999440, "Evangeline Thruway @ Kaliste Saloom", "ThwyKSaloom");
		add(30224920, -92009360, "Evangeline Thruway @ Louisiana", "NBThwyLou");
		add(30232980, -92012770, "Evangeline Thruway @ Mudd (NB)", "NBThwyMudd");
		add(30233190, -92014080, "Evangeline Thruway @ Mudd (SB)", "SBThwyMudd");
		add(30217690, -92004870, "Evangeline Thruway @ Pinhook", "ThwyPinhook");
		add(30231130, -92013800, "Evangeline Thruway @ Simcoe (SB)", "SBThwySimc");
		add(30171190, -91980040, "Evangeline Thruway @ Southpark", "ThwySouthpark");
		add(30209160, -91999960, "Evangeline Thruway @ University/Surrey", "ThwyUnivSur");
		add(30182820, -91989640, "Evangeline Thruway @ Verot School", "ThwyVSchool");
		add(30244530, -92011760, "Evangeline Thruway @ Willow (NB)", "NBThwyWillow");
		add(30244750, -92013390, "Evangeline Thruway @ Willow (SB)", "SBThwyWillow");
		
		add(30178090, -92074520, "Johnston @ Ambassador Caffery", "JohnstonCaffery");
		add(30207250, -92038300, "Johnston @ College", "JohnstonSCollege");
		add(30166240, -92083150, "Johnston @ Duhon", "JohnDuhon");
		add(30193690, -92058640, "Johnston @ Guilbeau/Camelia", "JohnstonCam");
		add(30212310, -92025410, "Johnston @ Lewis", "JohnLewis");
		add(30181340, -92071300, "Johnston @ Ridge", "JohnstonRidge");
		add(30217280, -92019080, "Johnston @ University", "JohnUniversity");
		add(30203390, -92048950, "Johnston @ Vital/Doucet", "JohnVitDou");
		
		add(30167350, -92036240, "Kaliste Saloom @ Camelia", "KSaloomCam");
		add(30172900, -92029740, "Kaliste Saloom @ Rue Louis XIV", "KalisteRueLouis");
		
		add(30295940, -92024530, "I-49 @ Gloria Switch", "I49RampGS");
		
		add(30234540, -92002330, "Louisiana @ Carmel", "LouisianaCarmel");
		add(30272370, -91992550, "Louisiana @ Pont Des Mouton", "LOUPDM");
		add(30245510, -92000410, "Louisiana @ Willow", "LouWillow");
		
		add(30233390, -91992640, "Pinhook @ Carmel", "PinCarmel");
		add(30188590, -92014120, "Pinhook @ Kaliste Saloom", "PinhookKSaloom");
		add(30160630, -91991100, "Pinhook @ Southpark", "PinhookSouthpark");
		add(30223600, -91998200, "Pinhook @ Surrey", "PinSurrey");
		add(30210510, -92009720, "Pinhook @ University", "PinUniv");
		add(30176740, -92002420, "Pinhook @ Verot School", "PinVerot");
		
		add(30251690, -92036890, "University @ I-10 WB", "UnivI10WB");
		add(30275910, -92049440, "University @ Pont Des Mouton", "UniversityPDMouton");
		add(30244510, -92032720, "University @ Willow", "UniversityWillow");
		
		add(30160450, -92022770, "Verot School @ Camellia", "VerotCamellia");
		
		add(30244020, -91988940, "Willow @ Teurlings", "WillowTeurlings");
	}
	
	public void add(int latitude, int longitude, String description, String image) {
		add(new CameraValueObject(latitude, longitude, description, image));
	}
}
