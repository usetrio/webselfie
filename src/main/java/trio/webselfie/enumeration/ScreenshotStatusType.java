package trio.webselfie.enumeration;

public enum ScreenshotStatusType {
	PROCESSING("Processing"),
	SUCCESS("Success"),
	FAIL("Fail");

	private final String name;

	ScreenshotStatusType(String name) {
		this.name = name;
	}
}
