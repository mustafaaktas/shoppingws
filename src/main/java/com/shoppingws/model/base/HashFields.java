package com.shoppingws.model.base;

public enum HashFields {
	KULLANICI() {
		@Override
		public String[] getHashFields() {
			String[] fields = { "personelNo" };
			return fields;
		}
	},
	
	MENU() {
		@Override
		public String[] getHashFields() {
			String[] fields = { "id", "today" };
			return fields;
		}
	},
	PAGE() {
		@Override
		public String[] getHashFields() {
			String[] fields = { "id", "today" };
			return fields;
		}
	},
	ROL(){
		@Override
		public String[] getHashFields() {
			String[] fields = {"id","rolKod","rolAd"};
			return fields;
		}
	},
	VIKISAYFA(){
		@Override
		public String[] getHashFields() {
			String[] fields = {"id","vikiUUID","vikiEtiket","parentId","rootId"};			
			return fields;
		}
	},
	METIN_TURU(){
		@Override
		public String[] getHashFields() {
			String[] fields = {"id","iliskiliViki","ustBilgiPath","altBilgiPath"};			
			return fields;
		}
	},
	TOPLULUK(){
		@Override
		public String[] getHashFields() {
			String[] fields = {"id"};			
			return fields;
		}
	},
	IDARI_METIN(){
		@Override
		public String[] getHashFields() {
			String[] fields = {"id","durum","idariMetinDurumu"};			
			return fields;
		}
	},FILE_META() {
		@Override
		public String[] getHashFields() {
			String[] fields = { "id" };
			return fields;
		}
	};
	
	
	public abstract String[] getHashFields();
}
