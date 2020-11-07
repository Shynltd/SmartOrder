let mongoose = require('mongoose');

let billSchema = new mongoose.Schema({
    billCode: {
        type: Number,
        required: true,
    },
    tableCode: {
        type: Number,
        required: true,
    },
    totalPrice: {
        type:Number,
        required:true
    },
    dateBill: {
        type: Date,
        required: true,
        default: Date.now()
    }

})
module.exports = mongoose.model('Bill', billSchema, 'bill');