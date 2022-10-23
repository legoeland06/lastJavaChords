package myjava.app;

/**
 * Interface générique fonctionnelle
 * 
 * @author ericbruneau
 *
 * @param <T> t
 */
public interface Transposable<T> {
/**
 * @param t 
 * @param object
 * @return object type T
 */
 T transpose(Integer t);
}
