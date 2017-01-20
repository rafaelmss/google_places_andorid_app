package google.places.bases.com.geolocationwithgoogleplaces.model;

/**
 * Created by Rafael mont'Alvão Seixas de Siqueira
 * email: rms.siqueira@gmail.com
 * .
 */

public class MyLocation {

    private String name;
    private String percentual;

    public MyLocation() {
    }

    public MyLocation(String name, String percentual) {
        this.name = name;
        this.percentual = percentual;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPercentual() {
        return percentual;
    }

    public void setPercentual(float percentual) {
        this.percentual = "100%";
    }

    @Override
    public String toString() {
        return "MyLocation{" +
                "name='" + name + '\'' +
                ", percentual='" + percentual + '\'' +
                '}';
    }
}
