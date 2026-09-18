import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";

const firebaseConfig = {
  apiKey: "AIzaSyC5o0DM02R2V7Jh6ECqHate4j2GQ9DpH60",
  authDomain: "taskflow-a5e73.firebaseapp.com",
  projectId: "taskflow-a5e73",
  storageBucket: "taskflow-a5e73.firebasestorage.app",
  messagingSenderId: "824178515154",
  appId: "1:824178515154:web:26d6155aec5e086dd5610b",
};

const app = initializeApp(firebaseConfig);

export const auth = getAuth(app);
