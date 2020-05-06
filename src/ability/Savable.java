package ability;

public interface Savable {

    String getJSONString();

    static void printReadingMessage(String componentName) {
        System.out.println("Reading " + componentName + " JSON...");
    }

    static void printReadingFinishedMessage(String componentName) {
        System.out.println(componentName + " JSON reading finished");

    }

}
