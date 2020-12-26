package com.example.furkan.fire_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;

public class FirebaseActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnResim,btnMetin,btnResimListele,btnMetinGoster,btnResimSil,btnMetinSil,btnResimGuncelle,btnMetinGuncelle;
    long starttime=0,stoptime=0;
    TextView tvSonuc,tvGoster,sure;

    String text1000="Lorem ipsum is a pseudo-Latin text used in web design, typography, layout, and printing in place of English to emphasise design elements over content. It's also called placeholder (or filler) text. It's a convenient tool for mock-ups. It helps to outline the visual elements of a document or presentation, eg typography, font, or layout. Lorem ipsum is mostly a part of a Latin text by the classical author and philosopher Cicero. Its words and letters have been changed by addition or removal, so to deliberately render its content nonsensical; it's not genuine, correct, or comprehensible Latin anymore. While lorem ipsum's still resembles classical Latin, it actually has no meaning whatsoever. As Cicero's text doesn't contain the letters K, W, or Z, alien to latin, these, and others are often inserted randomly to mimic the typographic appearence of European languages, as are digraphs not to be found in the original.n a professional context it often happens that private or corporate clients.";
    int adet=10;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference stRef=storage.getReference();
    FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference dbRef=db.getReference();

    final File localFile=File.createTempFile("images","jpg");

    public FirebaseActivity() throws IOException {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        btnResimListele = findViewById(R.id.btn_ResimGoster);
        btnResimListele.setOnClickListener(this);
        btnMetinGoster = findViewById(R.id.btn_MetinGoster);
        btnMetinGoster.setOnClickListener(this);

        btnMetin = findViewById(R.id.btn_Text);
        btnMetin.setOnClickListener(this);
        btnResim = findViewById(R.id.btn_Resim);
        btnResim.setOnClickListener(this);

        btnMetinSil = findViewById(R.id.btn_MetinSil);
        btnMetinSil.setOnClickListener(this);
        btnResimSil = findViewById(R.id.btn_ResimSil);
        btnResimSil.setOnClickListener(this);

        btnResimGuncelle = findViewById(R.id.btn_Resim_Guncelle);
        btnResimGuncelle.setOnClickListener(this);
        btnMetinGuncelle = findViewById(R.id.btn_Metin_Guncelle);
        btnMetinGuncelle.setOnClickListener(this);

        tvSonuc = findViewById(R.id.tv_Sonuc);
        tvGoster=findViewById(R.id.tv_Goster);

        sure=findViewById(R.id.adet);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Resim:
                resimEkle();
                break;
            case R.id.btn_Text:
                metinEkle();
                break;
            case R.id.btn_ResimGoster:
                resimGoster();
                break;
            case R.id.btn_MetinGoster:
                metinGoster();
                break;
            case R.id.btn_ResimSil:
                resimSil();
                break;
            case R.id.btn_MetinSil:
                metinSil();
                break;
            case R.id.btn_Resim_Guncelle:
                ResimGuncelle();
                break;
            case R.id.btn_Metin_Guncelle:
                metinGuncelle();
                break;
        }
    }

    private void metinGuncelle() {
        starttime=System.currentTimeMillis();
        Log.d("BASLANGIC_METİN_GUNCEL:",String.valueOf(starttime));
        for (int i=0;i<adet;i++) {
            dbRef=db.getReference().child(String.valueOf(i));
            dbRef.removeValue();
            DatabaseReference dbRefYeni = db.getReference(String.valueOf(i));
            final int finalI = i;
            dbRefYeni.setValue(text1000, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    long onsuccesstarttime=System.currentTimeMillis();
                    Log.d("SON_METİN_GUNCELLE:", finalI +".sure"+onsuccesstarttime);
                }
            });

        }

    }

    private void ResimGuncelle() {
        starttime=System.currentTimeMillis();
        Log.d("BASLANGIC_RESIM_GUNCEL:",String.valueOf(starttime));
        for (int i=0;i<adet;i++) {
            StorageReference resimRef = stRef.child(i+"_resim");
            final int finalI = i;
            resimRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Uri imageUri=Uri.parse("android.resource://com.example.furkan.fire_app/drawable/resim");
                        StorageReference ref = stRef.child(finalI + "_" + "resim");
                        ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                long onsuccesstarttime=System.currentTimeMillis();
                                Log.d("SON_RESİM_GUNCELLE:", finalI +".sure"+onsuccesstarttime);
                            }
                        });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                }
            });
        }

    }


    private void resimSil() {
        starttime=System.currentTimeMillis();
        Log.d("BASLANGIC_RESİM_SİL:",String.valueOf(starttime));
        for (int i=0;i<adet;i++) {
            StorageReference resimRef = stRef.child(i+"_resim");
            final int finalI = i;
            resimRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    long onsuccesstarttime=System.currentTimeMillis();
                    Log.d("SON_RESİM_SİL:", finalI +".sure"+onsuccesstarttime);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                }
            });
        }

    }

    private void resimGoster()  {
        starttime=System.currentTimeMillis();
        Log.d("BASLANGIC_RESİM_GOSTER:",String.valueOf(starttime));
        for(int i=0;i<adet;i++) {
            StorageReference resimRef = stRef.child(i+"_resim");
            final int finalI = i;
            resimRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    //Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    //imageView.setImageBitmap(bitmap);
                    long onsuccesstarttime=System.currentTimeMillis();
                    Log.d("SON_RESİM_GOSTER:", finalI +".sure"+onsuccesstarttime);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    private void resimEkle() {

        Uri imageUri=Uri.parse("android.resource://com.example.furkan.fire_app/drawable/resim");
        starttime=System.currentTimeMillis();
        Log.d("BASLANGIC_RESİM_EKLE:",String.valueOf(starttime));
        for (int i=0;i<adet;i++) {
            StorageReference ref = stRef.child(i + "_" + imageUri.getLastPathSegment());
            final int finalI = i;
            ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    long onsuccesstarttime=System.currentTimeMillis();
                    Log.d("SON_RESİM_EKLE:",finalI+".sure"+onsuccesstarttime);

                }
            });
        }

        long sonuc=stoptime-starttime;
        tvSonuc.setText("Resim Ekleme Süresi : "+String.valueOf(sonuc)+" ms");

    }


   private void metinGoster() {

        starttime=System.currentTimeMillis();
        Log.d("BASLANGIC_METİN_GOSTER:",String.valueOf(starttime));
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for(int i=0;i<adet;i++){
                   String text=dataSnapshot.child(i+"").getValue(String.class);
                   long onsuccesstarttime=System.currentTimeMillis();
                   Log.d("SON_METİN_GOSTER:", i +".sure"+onsuccesstarttime);
               }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void metinEkle() {
        starttime=System.currentTimeMillis();
        Log.d("BASLANGIC_METİN_EKLE:",String.valueOf(starttime));
        for (int i=0;i<adet;i++) {
            DatabaseReference dbRefYeni = db.getReference(String.valueOf(i));
            final int finalI = i;
            dbRefYeni.setValue(text1000, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    long onsuccesstarttime=System.currentTimeMillis();
                    Log.d("SON_METİN_EKLE:", finalI +".sure"+onsuccesstarttime);
                }
            });




        }


    }
    private void metinSil() {
        starttime=System.currentTimeMillis();
        Log.d("BASLANGIC_METİN_SİL:",String.valueOf(starttime));
        for (int i=0;i<adet;i++) {
            dbRef=db.getReference().child(String.valueOf(i));
            final int finalI = i;
            dbRef.removeValue(new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    long onsuccesstarttime=System.currentTimeMillis();
                    Log.d("SON_METİN_SİL:",finalI+".sure"+onsuccesstarttime);
                }
            });



        }

    }

}
