import java.util.Objects;

public class Grocery {

    private String name;
    private Nutrition nutrition;

    @Override
    public int hashCode() {
        return Objects.hash(name, nutrition);
    }
}
