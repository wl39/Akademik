import styles from './Modal.module.css'

const Modal = ({ handleClose, show, children, style }) => {
    const showHideClassName = show ? styles.displayBlock : styles.displayNone;

    return (
        <div className={styles.modal + ' ' + showHideClassName} onClick={handleClose}>
            <section className={styles.modalMain} style={style} onClick={e => {e.stopPropagation();}}>
                {children}
            </section>
        </div>
    );
};

export default Modal;