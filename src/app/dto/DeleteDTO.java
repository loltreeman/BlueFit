package app.dto;

public class DeleteDTO {

	private Boolean deleted;

	private Long pk;

	public DeleteDTO() {
	}

	public DeleteDTO(Boolean deleted, Long pk) {
		this.deleted = deleted;
		this.pk = pk;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}

	@Override
	public String toString() {
		return "DeleteDTO [deleted=" + deleted
				+ ", pk=" + pk + "]";
	}

}
