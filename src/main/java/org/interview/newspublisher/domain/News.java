package org.interview.newspublisher.domain;

import java.sql.Timestamp;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

/*
 * a simple domain entity doubling as a DTO
 */
@Entity
@Table(name = "news")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class News {

    @Id
    @GeneratedValue()
    private long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private int priority;
    
    @Column(nullable = false)
    private Timestamp time;

    
    @Column(nullable = false)
    private String heading;

    @Column(nullable = false)
    String content;

    public News() {
    }

    public News(String type, int priority, Timestamp time,
			String heading, String content) {
		super();
		this.type = type;
		this.priority = priority;
		this.time = time;
		this.heading = heading;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", type=" + type + ", priority=" + priority
				+ ", time=" + time + ", heading=" + heading + ", content="
				+ content + "]";
	}
    
}
