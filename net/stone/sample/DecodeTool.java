package stone.sample;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class DecodeTool {
	public static void main(String[] args) throws IOException, InterruptedException {
		//System.out.println("decode tool main");
		CommandLineParser parser = new PosixParser();
		CommandLine commandLine = null;

	    // load options
	    _Options();

        try {
            commandLine = parser.parse(allOptions, args, false);
        } catch (ParseException ex) {
            System.err.println(ex.getMessage());
            //usage(commandLine);
            return;
        }
        
        boolean cmdFound = false;
        
        for (int i =0;i<commandLine.getArgs().length;i++) {
        	String opt = commandLine.getArgs()[i];
            if (opt.equalsIgnoreCase("b") || opt.equalsIgnoreCase("build")) {
            	cmdBuild(commandLine);
                cmdFound = true;
            }else if(opt.equalsIgnoreCase("r") || opt.equalsIgnoreCase("read")){
            	cmdRead(commandLine);
            	cmdFound = true;
            }
        }
        if (cmdFound == false) {
	        if (commandLine.hasOption("version") || commandLine.hasOption("version")) {
	            _version();
	        }else
	        {
	        	usage(commandLine);
	        }
        }
	}
	private static void cmdRead(CommandLine cli){
		String[] args = cli.getArgs();
        String appDirName = args.length < 2 ? "." : args[1];
        String xmlPath = appDirName + "/AndroidManifest.xml";
        DOMParser parser = new DOMParser();
        Document document = parser.parse(xmlPath);
        //get root element 
        Element rootElement = document.getDocumentElement(); 
        NodeList application = rootElement.getElementsByTagName("application");
        
        System.out.println("cmdRead");
        NodeList nodes = application.item(0).getChildNodes();
        // print activites
        // NodeList nodes = rootElement.getChildNodes();
        for (int i=0; i < nodes.getLength(); i++) 
        { 
        	
        	Node node = nodes.item(i);
        	System.out.println(node.getNodeName());
        	if(node.getNodeName().equals("activity")){
        		//activiy不一定有android:name吧？
        		EntryActivity activity = new EntryActivity();
        		activity.setName(node.getAttributes().getNamedItem("android:name").getNodeValue());
        		activity.setData(node);
        		parser.parseActivity(node, activity);
//        		HashMap children = parser.parseChildNodes(node);
        		
//        		if(parser.containsKey(children, "intent-filter")){
//        			activity.setMain(true);
//        		}
//        		Iterator iterator = children.entrySet().iterator();
//        		while (iterator.hasNext()) {
//        			Map.Entry entry = (Entry) iterator.next();
//					Node xmlnode = (Node) entry.getValue();
//					System.out.println("---"+xmlnode.getNodeName());
//				}
        		
        		System.out.println("--ismain"+activity.isMain());
        		if(activity.getFilters().size()>0){
        			System.out.println("--filtername"+activity.getFilters().get(0).getName());
        		}
        		
        		parser.names.put(activity.getName(), activity);
        		
        		
        	}
        	
        	NamedNodeMap attrs = node.getAttributes();  
        	if( attrs != null){
        		for(int j = 0 ; j<attrs.getLength() ; j++) {
        			Attr attribute = (Attr)attrs.item(j);     
        			System.out.println("+" + attribute.getName()+" = "+attribute.getValue());
        		}
        	}
        	else{
        		System.out.println("-" + node.getNodeName());
        	}

//           if (node.getNodeType() == Node.ELEMENT_NODE) {   
//              Element child = (Element) node; 
//              System.out.println(child.getNodeValue());
//              //process child element 
//           } 
        } 
        
        parser.addActivity(document, "net.stone.SplashActivity");
        parser.switchMain(document, "net.stone.SplashActivity");
        //act.setAsMainActivity();
        
        
        
	}
	private static void cmdBuild(CommandLine cli) {
		String[] args = cli.getArgs();
        String appDirName = args.length < 2 ? "." : args[1];
//        File outFile = null;
//        if (cli.hasOption("o") || cli.hasOption("output")) {
//            outFile = new File(cli.getOptionValue("o"));
//        }
        //ApkOptions apkOptions = new ApkOptions();
        String cmdString = "java -jar apktool_2.0.3.jar b " + appDirName;
        Command.exeCmd(cmdString);
        //new Androlib(apkOptions).build(new File(appDirName), outFile);
        
	}
	private static void usage(CommandLine commandLine) {

	        // load basicOptions
	        _Options();
	        HelpFormatter formatter = new HelpFormatter();
	        formatter.setWidth(120);

	        // print out license info prior to formatter.
	        System.out.println(
	                "Apktool v" + _getVersion() + " - a tool for reengineering Android apk files\n" +
	                       // "with smali v" + ApktoolProperties.get("smaliVersion") +
	                      //  " and baksmali v" + ApktoolProperties.get("baksmaliVersion") + "\n" +
	                        "Copyright 2016 Sidney <sidney9111@gmail.com>\n" +
	                        "" );
	        formatter.printHelp("apktool ", allOptions);
	        formatter.printHelp("apktool g", allOptions);
	        
	  }
	private static String _getVersion(){
		return "0.0.1";
	}
	private static void _version(){
		System.out.println(_getVersion());
	}
	private static void _Options() {

	    // create options
		Option versionOption = OptionBuilder.withLongOpt("version")
	                .withDescription("prints the version then exits")
	                .create("version");
		allOptions.addOption(versionOption);
	}
	private final static Options allOptions;
	static {
        //normal and advance usage output
//        normalOptions = new Options();
//        BuildOptions = new Options();
//        DecodeOptions = new Options();
//        frameOptions = new Options();
        allOptions = new Options();
//        emptyOptions = new Options();
    }
}
