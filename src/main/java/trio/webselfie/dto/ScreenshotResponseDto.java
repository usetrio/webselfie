package trio.webselfie.dto;

import trio.webselfie.enumeration.ScreenshotStatusType;

import java.time.LocalDateTime;

public class ScreenshotResponseDto {

	private Long id;
	private ScreenshotStatusType status;
	private String url;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ScreenshotStatusType getStatus() {
		return status;
	}

	public void setStatus(ScreenshotStatusType status) {
		this.status = status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}
