package dev.taskflow.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents a task entity for persistence using JPA.
 * <p>
 * This class is implemented as a Java record, which means it is immutable and 
 * automatically provides implementations for equals, hashCode, and toString.
 * <p>
 * Note: Using records as JPA entities may have limitations, such as lack of 
 * support for mutable fields and default constructors required by some JPA providers.
 */
@Entity
public class Task {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(length = 200, nullable = false)
	private String title;
	private boolean done;

	protected Task() { // for JPA only
	}

    public Task(String title) {
        this.title = title;
    }

	public long id() {
		return id;
	}

	public String title() {
		return title;
	}

	public void rename(String title) {
		this.title = title;
	}

	public boolean done() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
}