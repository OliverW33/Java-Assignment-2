import org.json.simple.*;

public class RequestClass {

    protected String _class = "Request";

    protected JSONObject newJSONObj()
    {
        JSONObject newObject = new JSONObject();
        newObject.put("_class", _class);
        return newObject;
    }

}
