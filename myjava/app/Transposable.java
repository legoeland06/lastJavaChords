package myjava.app;

/**
 * Interface générique fonctionnelle
 * 
 * @author ericbruneau
 *
 * @param <T>
 */
public interface Transposable<T> {
/**
 * @param object
 * @return object type T
 */
 T transpose(Integer t);
}
