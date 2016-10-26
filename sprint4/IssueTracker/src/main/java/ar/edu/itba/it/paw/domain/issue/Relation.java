package ar.edu.itba.it.paw.domain.issue;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class Relation {

	@Enumerated(EnumType.STRING)
	private RelationType relationType;
	@ManyToOne
	@JoinColumn(name="related_issue_fk")
	private Issue relatedIssue;
	
	private Relation() {
		
	}

	public Relation(RelationType relationType, Issue realtedIssue, Issue belongsTo) {
		setRelationType(relationType);
		setRelatedIssue(realtedIssue);
	}

	public RelationType getRelationType() {
		return relationType;
	}

	private void setRelationType(RelationType relationType) {
		this.relationType = relationType;
	}

	public Issue getRelatedIssue() {
		return relatedIssue;
	}

	private void setRelatedIssue(Issue relatedIssue) {
		this.relatedIssue = relatedIssue;
	}


	@Override
	public String toString() {
		return relatedIssue.getTitle() + " - " + relationType.getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((relatedIssue == null) ? 0 : relatedIssue.hashCode());
		result = prime * result
				+ ((relationType == null) ? 0 : relationType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Relation other = (Relation) obj;
		if (relatedIssue == null) {
			if (other.relatedIssue != null)
				return false;
		} else if (!relatedIssue.equals(other.relatedIssue))
			return false;
		if (relationType == null) {
			if (other.relationType != null)
				return false;
		} else if (!relationType.equals(other.relationType))
			return false;
		return true;
	}
}
