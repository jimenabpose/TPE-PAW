package ar.edu.itba.it.paw.web.command;

import java.util.Date;

import ar.edu.itba.it.paw.domain.Version;
import ar.edu.itba.it.paw.domain.VersionState;

public class CreateVersionForm {

	private Version version;
	private String verName;
	private String verDescription;
	private Date verDate;
	private VersionState verState;
	
	public CreateVersionForm(){
		
	}
	
	public CreateVersionForm(Version version){
		this.setVersion(version);
		this.setVerDescription(version.getDescription());
		this.setVerName(version.getName());
		this.setVerDate(version.getReleaseDate());
		this.setVerState(version.getState());
	}

	public String getVerName() {
		return verName;
	}

	public void setVerName(String verName) {
		this.verName = verName;
	}

	public String getVerDescription() {
		return verDescription;
	}

	public void setVerDescription(String verDescription) {
		this.verDescription = verDescription;
	}

	public Date getVerDate() {
		return verDate;
	}

	public void setVerDate(Date verDate) {
		this.verDate = verDate;
	}

	public VersionState getVerState() {
		return verState;
	}

	public void setVerState(VersionState verState) {
		this.verState = verState;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public Version getVersion() {
		return version;
	}	
	
	public Version getRelatedVersion() {
		
		/*Si es nuevo lo creo*/
		if(this.getVersion() == null) {
			return new Version(this.getVerName(), this.getVerDescription(), this.getVerDate(),
					VersionState.OPEN);
		} else {
			this.getVersion().setName(this.getVerName());
			this.getVersion().setDescription(this.getVerDescription());
			this.getVersion().setReleaseDate(this.getVerDate());
			this.getVersion().setState(this.getVerState());
			
			return this.getVersion();
		}
	}
}
