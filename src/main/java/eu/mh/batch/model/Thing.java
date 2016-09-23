package eu.mh.batch.model;

public class Thing {
	
    private String descriptor;
    private String name;

    public Thing() {

    }

    public Thing(String name, String descriptor) {
        this.name = name;
        this.descriptor = descriptor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    @Override
    public String toString() {
        return "name: " + name + ", descriptor: " + descriptor;
    }

}
