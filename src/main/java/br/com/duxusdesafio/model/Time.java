package br.com.duxusdesafio.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "time")
public class Time {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
    private LocalDate data;
	
	@JsonIgnore
	@OneToMany(mappedBy = "time", cascade = CascadeType.ALL)
	private List<ComposicaoTime> composicaoTime;

	public Time() {
	}

	public Time(LocalDate data, List<ComposicaoTime> composicaoTime) {
		this.data = data;
		this.composicaoTime = composicaoTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public List<ComposicaoTime> getComposicaoTime() {
		return composicaoTime;
	}

	public void setComposicaoTime(List<ComposicaoTime> composicaoTime) {
		this.composicaoTime = composicaoTime;
	}


	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Time)) return false;
		Time time = (Time) o;
		return id == time.id && Objects.equals(data, time.data);
	}

	@Override
	public final int hashCode() {
		return Objects.hash(id, data);
	}

	@Override
	public String toString() {
		return "Time{" +
				"id=" + id +
				", data=" + data +
				'}';
	}
}
