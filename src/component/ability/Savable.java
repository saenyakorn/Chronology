package component.ability;

public interface Savable {

    static void printReadingMessage(String componentName) {
        System.out.println("Reading " + componentName + " JSON...");
    }

    static void printReadingFinishedMessage(String componentName) {
        System.out.println(componentName + " JSON reading finished");

    }

    String getJSONString();

}
