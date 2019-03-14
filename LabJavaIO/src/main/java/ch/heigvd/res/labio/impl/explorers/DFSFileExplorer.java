package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import java.io.File;
import java.lang.reflect.Array;

//Ajout pour le trie du tableau
import java.util.Arrays;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 * 
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor vistor) {
    if(rootDirectory != null){
      //On parcours le root directory
      vistor.visit(rootDirectory);

      //On récupère la liste de se que contient le root directory
      File[] fichiers = rootDirectory.listFiles();

      if(fichiers != null){
        //Trie du tableau
        Arrays.sort(fichiers);

        //Parcours du contenu de root
        for(int i = 0; i < fichiers.length; ++i){
          //Distinction entre fichiers et dossiers
          if(fichiers[i].isDirectory()) {
            explore(fichiers[i], vistor);
          } else {
            vistor.visit(fichiers[i]);
          }
        }
      }
    }
  }

}
