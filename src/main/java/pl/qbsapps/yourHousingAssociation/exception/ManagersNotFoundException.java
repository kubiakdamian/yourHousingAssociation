package pl.qbsapps.yourHousingAssociation.exception;

import pl.qbsapps.yourHousingAssociation.model.exception.ExceptionCode;

public class ManagersNotFoundException extends AbstractNotFoundException {
    public ManagersNotFoundException(String message, ExceptionCode code) {
        super(message, code);
    }

    public ManagersNotFoundException(String message, Throwable cause, ExceptionCode code) {
        super(message, cause, code);
    }

    public ManagersNotFoundException(){
        super("Managers not found", ExceptionCode.MANAGERS_NOT_FOUND);
    }
}
