package education.response;

public class Response {
    private boolean status;
    private String msg;

    public Response(String msg, boolean status){
        this.msg = msg;
        this.status = status;
    }
    public String getErr() {
        return msg;
    }

    public void setErr(String msg) {
        this.msg = msg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
