package com.example;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.common.io.ClassPathResource;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.IOException;

public class ChessAI {

    /**
     * Main function that loads a model from the classpath, generates an example input,
     * performs a prediction using the model, and prints the output of the model.
     *
     * @param  args  the command line arguments (not used)
     * @throws IOException  if there is an error loading the model from the classpath
    */
    public static void main(String[] args) throws IOException {
        // Caricamento del modello dalla risorsa classpath
        MultiLayerNetwork model = loadModel();

        // Esempio di input (da sostituire con il tuo input reale)
        INDArray input = Nd4j.rand(8, 8, 14);

        // Effettua la predizione
        INDArray output = model.output(input);

        // Stampa l'output del modello
        System.out.println("Output del modello: ");
        System.out.println(output.toString());
    }

    // Metodo per caricare il modello da un file H5
    private static MultiLayerNetwork loadModel() {
        // Caricamento dei pesi del modello
        String modelPath = null;
        try {
            modelPath = new ClassPathResource("modelversion3.h5").getFile().getPath();
            return org.deeplearning4j.nn.modelimport.keras.KerasModelImport.importKerasSequentialModelAndWeights(modelPath);
        } catch (IOException | org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException | org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException e) {
            e.printStackTrace();
        }
        // Se il caricamento del modello fallisce, gestisci l'eccezione e ritorna null
        return null;
    }
    
}
