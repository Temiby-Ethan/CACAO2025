package abstraction.eq5Transformateur2;

public class Transformateur2 extends Transformateur2Acteur  {
	
	public Transformateur2() {
		super();
		private journal;

		public Transformateur2() {
			super();
			this.journal = setJournaux();
		}

		public void next() {
			super.next();
			journal.info("Étape numéro: " + this.getEtape());
		}

	}
}
