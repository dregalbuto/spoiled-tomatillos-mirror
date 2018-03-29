package edu.northeastern.cs4500.spoiledtomatillos;

public class JsonStrings {
	public static final String EMAIL = "email";
	public static final String USER_EMAIL = "userEmail";
	public static final String USER_ID = "userId";
	public static final String TOKEN = "token";
	public static final String MESSAGE = "message";
	public static final String GROUP_ID = "groupId";
	public static final String GROUP_NAME = "groupName";
	public static final String BLACKLIST = "blacklist";
	public static final String MOVIE_ID = "movieId";
	public static final String RATING = "rating";
	public static final String TEXT = "text";
	public static final String REVIEW_ID = "reviewId";
	public static final String TARGET_EMAIL = "targetEmail";
	public static final String SECRET = "password";
	public static final String LOGGED_IN = "Logged in successfully";

	public static final String SUCCESS = "Success";
	public static final String ERROR = "Error processing request";
	public static final String INVALID_LOGIN = "Not a valid login";
	public static final String TOKEN_EXPIRED = "User is not logged in";
	public static final String MOVIE_NOT_FOUND = "Movie not found";
	public static final String REVIEW_NOT_FOUND = "Review not found";
	public static final String USER_NOT_FOUND = "User not found";
	public static final String USER_DISABLED = "User disabled";
	public static final String TARGET_USER_NOT_FOUND = "Target user not found";
	public static final String CANNOT_JOIN = "Cannot join group";
	public static final String CANNOT_LEAVE = "You are not a member of this group";
	public static final String NO_PERMISSION = "You do not have permission to perform this action";
	public static final String BAD_SECRET = "Incorrect password";
	public static final String ILLEGAL_ACCESS = "Illegal access!";
	public static final String USER_EXISTS = "Account with this email already exists";

	private JsonStrings() {
		throw new IllegalStateException("Utility class");
	}
}
