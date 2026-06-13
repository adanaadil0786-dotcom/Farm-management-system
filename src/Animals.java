
public class Animals{
    public String getAnimalname() {
        return animalname;
    }

    public void setAnimalname(String animalname) {
        this.animalname = animalname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHealth_status() {
        return health_status;
    }

    public void setHealth_status(String health_status) {
        this.health_status = health_status;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Animals(int animalId, String animalname, int age, String health_status, String weight) {
        this.animalId = animalId;
        this.animalname = animalname;
        this.age = age;
        this.health_status = health_status;
        this.weight = weight;
    }

    int animalId;

    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    String animalname;
    int age;
    String health_status;
    String weight;
}


