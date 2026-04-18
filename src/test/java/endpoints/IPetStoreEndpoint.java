package endpoints;

public interface IPetStoreEndpoint {

	/* -----------------------Pet Module-----------------------*/
	public static final String UPLOAD_PET_IMG = "/pet/{petId}/uploadImage";
	public static final String ADD_PET = "/pet";
	public static final String UPDATE_PET = "/pet";
	public static final String GET_PET_BY_STATUS = "/pet/findByStatus";
	public static final String GET_PET_BY_ID = "/pet/{petId}";
	public static final String UPDATE_PET_USING_FORM = "/pet/petId";
	public static final String DELETE_PET_BY_ID = "/pet/{petId}";
	
	/*----------------------Store Module-----------------------*/
	public static final String GET_INVENTORY = "/store/inventory";
	public static final String CREATE_ORDER = "/store/order";
	public static final String GET_ORDER_BY_ID = "/store/order/{orderId}";
	public static final String DELETE_ORDER_ID = "/store/order/{orderId}";
	
	/*----------------------User Module------------------------*/
	public static final String GET_USER = "/user/{username}";
	public static final String UPDATE_USER = "/user/{username}";
	public static final String DELETE_USER = "/user/{username}";
	public static final String USER_LOGIN = "/user/login";
	public static final String CREATE_USER = "/user";
	public static final String USER_LOGOUT = "/user/logout";
	
}
