const admin = require('firebase-admin');
const serviceAccount = require('./serviceAccountKey.json');
const fishData = require('./fish_data.json');

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

const db = admin.firestore();

async function importFishData() {
  const batch = db.batch();
  
  fishData.fish.forEach((fish) => {
    const docRef = db.collection('fish').doc();
    const fishWithLowerName = {
      ...fish,
      nameLower: fish.name.toLowerCase()
    };
    batch.set(docRef, fishWithLowerName);
  });

  try {
    await batch.commit();
    console.log('Fish data imported successfully!');
  } catch (error) {
    console.error('Error importing fish data:', error);
  }
}

importFishData().then(() => process.exit()); 