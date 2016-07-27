package indi.kaixuan.dm.dao.exception;

/**
 * 自定义Dao异常
 * @author Administrator
 *
 */
public class DaoException extends Exception {

	private static final long serialVersionUID = 1L;

	public DaoException() {
		super();
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoException(String message) {
		super(message);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}

}
