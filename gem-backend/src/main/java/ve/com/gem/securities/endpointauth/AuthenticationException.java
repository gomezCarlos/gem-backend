package ve.com.gem.securities.endpointauth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED, reason="Usuario no autorizado")
public class AuthenticationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6243831068518504219L;

	public AuthenticationException() {
		// TODO Auto-generated constructor stub
	}

	public AuthenticationException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public AuthenticationException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public AuthenticationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
