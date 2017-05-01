package http_server;


public class ConfigurationValidation {

  public void exitForInvalidArgs(String[] commandLineArgs){
   if (!isValidArgs(commandLineArgs)){
     System.out.println("Invalid Arguments");
     System.exit(0);
   }
  }

  public Boolean isValidArgs(String[] commandLineArgs) {
    return isValidFlags(commandLineArgs) && isValidPortNumber(commandLineArgs);
  }

  private Boolean isValidFlags(String[] commandLineArgs) {
    if (commandLineArgs.length == 2) {
      return commandLineArgs[0].equals("-p") || commandLineArgs[0].equals("-d");
    }
    if (commandLineArgs.length == 4) {
      return commandLineArgs[0].equals("-p") && commandLineArgs[2].equals("-d");
    }
    return false;
  }

  private Boolean isValidPortNumber(String[] commandLineArgs) {
    Integer maxPortValue = 65535;
    Integer minPortValue = 0;
    String portNumber = getPortNumber(commandLineArgs);
    Integer port = Integer.parseInt(portNumber);
    return !((port > maxPortValue) || (port < minPortValue));
  }

  private String getPortNumber(String[] commandLineArgs) {
    Configuration configuration = new Configuration();
    return configuration.findArg(commandLineArgs, "-p", null);
  }
}
