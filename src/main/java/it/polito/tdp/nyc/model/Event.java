package it.polito.tdp.nyc.model;


public class Event  implements Comparable<Event> {
		public enum EventType{//posso chiamare tipo, eventtype come voglio
			INIZIO_REV,
			FINE_REV
			}
		
		private EventType type;
		private int time;
		private String quartiere;
		private Integer tecnico;
		
		public Event(EventType type, int time, String quartiere,Integer tecnico) {
			super();
			this.type = type;
			this.time = time;
			this.quartiere = quartiere;
			this.tecnico=tecnico;
		}
		public EventType getType() {
			return type;
		}
		public int getTime() {
			return time;
		}
		public String getQuartiere() {
			return quartiere;
		}
		
		
		public Integer getTecnico() {
			return tecnico;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((quartiere == null) ? 0 : quartiere.hashCode());
			result = prime * result + time;
			result = prime * result + ((type == null) ? 0 : type.hashCode());
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
			Event other = (Event) obj;
			if (quartiere == null) {
				if (other.quartiere != null)
					return false;
			} else if (!quartiere.equals(other.quartiere))
				return false;
			if (time != other.time)
				return false;
			if (type != other.type)
				return false;
			return true;
		}
		@Override
		public int compareTo(Event o) {
			// TODO Auto-generated method stub
			return this.time-o.time;
		}
		

}
