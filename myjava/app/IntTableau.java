package myjava.app;

/**
 * @author ericbruneau
 *
 */
public enum IntTableau{
    /**
     * 
     */
    INSTANCE;

    private IntTableau(){
    }

    /**
     * @param nbElement : longueur du tableau
     * @param first : premier élément
     * @param increment
     * @return Retourne un tableau incrémenté
     */
    protected int[] getRangeInt(int nbElement,int first,int increment){

        int[] tab = new int[nbElement];

        for (int i=0;i<=nbElement-1;i++) 
            tab[i]=first+i*increment;
            
        return tab;
    }
}