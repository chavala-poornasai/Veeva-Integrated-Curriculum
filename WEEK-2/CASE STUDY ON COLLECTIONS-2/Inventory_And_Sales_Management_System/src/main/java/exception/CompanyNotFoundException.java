package exception;

public class CompanyNotFoundException  extends RuntimeException{
    public CompanyNotFoundException(String Message) {
        super(Message);
    }
}
