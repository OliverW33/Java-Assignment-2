import org.json.simple.*;

public class ErrorResponseClass extends RequestClass{

   String errorMessage = "";

   public ErrorResponseClass(String newErrorMessage)
   {
       _class = "ErrorResponse";
       errorMessage = newErrorMessage;
   }

    @Override
    public JSONObject newJSONObj()
    {
        JSONObject newObject = new JSONObject();
        newObject.put("_class", _class);
        newObject.put("error", errorMessage);
        return newObject;
    }

}
