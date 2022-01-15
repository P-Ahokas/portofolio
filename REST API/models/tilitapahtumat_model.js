const db = require('../database');

const tilitapahtumat = {
  getById: function(id, callback) {
    return db.query('select * from tilitapahtumat where tilinro=?', [id], callback);
  },
  getAll: function(callback) {
    return db.query('select * from tilitapahtumat', callback);
  },
  add: function(tilitapahtumat, callback) {
    return db.query(
      'insert into tilitapahtumat (kortti_korttinumero, tilinro, aika, asiakasnro, amount, selite) values(?,?,NOW(),?,?,?)', 
      [tilitapahtumat.kortti_korttinumero, tilitapahtumat.tilinro, tilitapahtumat.aika, tilitapahtumat.asiakasnro, tilitapahtumat.id_tilitapahtuma, tilitapahtumat.amount, tilitapahtumat.selite],
      callback
    );
  },
  delete: function(id, callback) {
    return db.query('delete from tilitapahtumat where id_tilitapahtuma=?', [id], callback);
  },
  update: function(id, tilitapahtumat, callback) {
    return db.query(
      'update tilitapahtumat set kortti_korttinumero=?, tilinro=?, aika=NOW(), asiakasnro=?, amount=?, selite=? where id_tilitapahtuma=?',
      [tilitapahtumat.kortti_korttinumero, tilitapahtumat.tilinro, tilitapahtumat.aika, tilitapahtumat.asiakasnro, tilitapahtumat.amount, tilitapahtumat.selite, id],
      callback
    );
  }
};
module.exports = tilitapahtumat;