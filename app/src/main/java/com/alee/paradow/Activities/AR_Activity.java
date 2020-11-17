package com.alee.paradow.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alee.paradow.R;
import com.alee.paradow.utils.Links;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideApp;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class AR_Activity extends AppCompatActivity {

    ArFragment arFragment;
    String image;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_r);


        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);
        Intent intent = getIntent();
        image = intent.getStringExtra("image");
        flag = true;

        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                Toast.makeText(getApplication(), "clicked", Toast.LENGTH_LONG).show();
                if (flag){
                    Anchor anchor = hitResult.createAnchor();
                    addNodeToScene(arFragment, anchor);
                    flag = false;
                }
            }
        });

    }

    private void addNodeToScene(ArFragment fragment, Anchor anchor) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode postitNode = new TransformableNode(fragment.getTransformationSystem());
//        postitNode.setRenderable(renderable);
        postitNode.setParent(anchorNode);

        //add text view node
        ViewRenderable.builder().setView(this, R.layout.controls).build()
                .thenAccept(viewRenderable -> {
                    Node noteText = new Node();
                    noteText.setParent(fragment.getArSceneView().getScene());
                    noteText.setParent(postitNode);
                    noteText.setRenderable(viewRenderable);

                    ImageView imageView = viewRenderable.getView().findViewById(R.id.info_button);

                    Log.i("AR Activity" , "showing image "+image);
                    GlideApp.with(this)
                            .load(Links.IMAGE_BASE_URL + image)
                            .placeholder(R.drawable.loading)
                            .error(R.mipmap.ic_launcher)
                            .into(imageView);

                    //set the note position in relation to its parent node.
                    // In this case, along the y axis of the Post-It note
                    noteText.setLocalPosition(new Vector3(0f, .5f, 0f));
                });

        //code for editing text removed for brevity

        fragment.getArSceneView().getScene().addChild(anchorNode);
        postitNode.select();
    }
}
