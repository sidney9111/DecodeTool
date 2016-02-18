package stone.sample;

import java.io.Console;
import java.io.File;
import java.io.IOException;

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
        DOMParser parser=new DOMParser();
        Document document = parser.parse(xmlPath);
        //get root element 
        Element rootElement = document.getDocumentElement(); 
        NodeList nodes = rootElement.getChildNodes(); 
        for (int i=0; i < nodes.getLength(); i++) 
        { 
           Node node = nodes.item(i);
           NamedNodeMap attrs = node.getAttributes();  
           if( attrs != null){
	           for(int j = 0 ; j<attrs.getLength() ; j++) {
	             Attr attribute = (Attr)attrs.item(j);     
	             System.out.println(" " + attribute.getName()+" = "+attribute.getValue());
	           }
	           
           }
		   else{
			   System.out.println("-" + node.getNodeName());
		   }
//           
//           System.out.println(node.getNodeValue());
//           if (node.getNodeType() == Node.ELEMENT_NODE) {   
//              Element child = (Element) node; 
//              System.out.println(child.getNodeValue());
//              //process child element 
//           } 
        } 
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
