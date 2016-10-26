package ar.edu.itba.it.paw.domain;

public class IssueAccesses implements Comparable<IssueAccesses>{

	private Issue issue;
	private int quantity;
	
	public IssueAccesses(Issue issue, int quantity){
		this.setIssue(issue);
		this.quantity = quantity;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public Issue getIssue() {
		return issue;
	}

	public void incQuantity() {
		this.quantity++;
	}

	public int getQuantity() {
		return quantity;
	}
	
	@Override
	public int compareTo(IssueAccesses issueacc) {
		if(this.quantity > issueacc.getQuantity())
            return -1;
        else if(this.quantity == issueacc.getQuantity())
            return 0;
        else
            return 1;
	}
	
	@Override
	public String toString() {
		return "IssueAccesses [issue=" + issue + ", quantity=" + quantity + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((issue == null) ? 0 : issue.hashCode());
		result = prime * result + quantity;
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
		IssueAccesses other = (IssueAccesses) obj;
		if (issue == null) {
			if (other.issue != null)
				return false;
		} else if (!issue.equals(other.issue))
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}

}
