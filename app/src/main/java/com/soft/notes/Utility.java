package com.soft.notes;

import android.content.Context;  // Ensure this import is present
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;

public class Utility {

    // Method to show a toast message
    static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    // Method to get a reference to the Firestore collection for notes
    static CollectionReference getCollectionReferenceForNotes() {
        // Get the current user from Firebase Authentication
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Check if the user is not null to avoid NullPointerException
        if (currentUser != null) {
            // Return the reference to the "my_notes" collection for the current user
            return FirebaseFirestore.getInstance()
                    .collection("notes")
                    .document(currentUser.getUid())
                    .collection("my_notes");
        } else {
            // Handle the case where there is no currently authenticated user
            throw new IllegalStateException("No authenticated user found");
        }
    }
    static String timestampToString(Timestamp timestamp) {
        return new SimpleDateFormat("MM/dd/yyyy").format(timestamp.toDate());
    }

}

