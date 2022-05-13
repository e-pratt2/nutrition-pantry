package Filters;

public class AlwaysPassFilter implements Filter {
    @Override
    public boolean accepts(Object e) {
        return true;
    }
}
