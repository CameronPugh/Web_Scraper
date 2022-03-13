import java.net.URI;
import java.util.Objects;

public class Result {

    private String url;
    private URI uri;
    private int id;
    private String type;
    private String isSafe;

    Result(String url, int id, String type){
        this.id = id;
        this.type = type;
        this.url = url;
        this.isSafe = "false";
    }

    public int getId() {
        return id;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public String getIsSafe() {
        return isSafe;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIsSafe(String isSafe) {
        this.isSafe = isSafe;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return id == result.id && Objects.equals(url, result.url) && Objects.equals(type, result.type) && Objects.equals(isSafe, result.isSafe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, id, type, isSafe);
    }
}
