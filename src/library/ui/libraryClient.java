/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.ui;

import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import library.entity.*;
import library.entity.unite;
import library.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Shadow
 */
public class libraryClient extends javax.swing.JFrame {
   
    /**
     * Creates new form libraryClient
     */
    public libraryClient() {
        initComponents();
    }
    private static String QUERY_PRINT_ALL_BOOKS="from Links L";
    private static String QUERY_ON_DELETING="from Links  L where L.books.bookName like '";
    private static String QUERY_ON_EDITING="from Books B where B.bookName like '";
    private static String QUERY_AUTHORS_FIND="select A.authorId from Authors A where A.authorName like'";
    
    private void runShowAll()
    {
        executeHQLQuery(QUERY_PRINT_ALL_BOOKS);
    }
    private void executeHQLQuery(String hql)
    {
        try
        {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            List<unite> resultList = q.list();
            displayResult(resultList);
            session.getTransaction().commit();
            session.close();
        }
        catch(HibernateException he)
        {
            he.printStackTrace();
        }
    }
    private void editStatus(String BookName, String StatusName)
    {
        if(!BookName.trim().equals("") && BookName.isEmpty()!=true && StatusName.trim()!="" && StatusName.isEmpty()!=true)
        {
            try
            {
                Session session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                Query onEditing = session.createQuery(QUERY_ON_EDITING+BookName+"'");
                Books book =(Books) onEditing.uniqueResult();
                book.setBookStatus(StatusName);
                session.update(book);
                session.getTransaction().commit();
                session.close();
            }
            catch(HibernateException he)
            {
                he.printStackTrace();
            }
        }
    }
    private void deleteBook(String deleteBookName)
    {
        if(!deleteBookName.trim().equals("") && deleteBookName.isEmpty()!=true)
        {
            try
            {
                Session session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                Query onDeleting = session.createQuery(QUERY_ON_DELETING+deleteBookName+"'");
                List QoD = onDeleting.list();
                for(Object o: QoD)
                {
                    Links link = (Links) o;
                    session.delete(link);
                    session.delete(link.getBooks());
                }
                session.getTransaction().commit();
                session.close();
            }
            catch(HibernateException he)
                {he.printStackTrace();}

        }
    }
    private void addBook(String bookName, String[] AuthorNames)
    {
      if(!bookName.trim().equals("")&& bookName.isEmpty()!=true)
        {
            try
            {
                Session session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                Books book = new Books();
                book.setBookName(bookName);
                book.setBookStatus("is exist");
                for(int i=0;i<AuthorNames.length;i++)
                {
                
                Authors author = new Authors();
                if(AuthorNames[i].trim().equals("") || AuthorNames[i].isEmpty()==true)
                {author.setAuthorName("Unknown");}
                    author = new Authors(AuthorNames[i]);
                    session.save(author);
                    Links link = new Links();
                    link.setBooks(book);
                    link.setAuthors(author);
                    session.save(author);
                    session.save(link);
                }
                
                session.save(book);
                session.getTransaction().commit();
                session.close();
            }
            catch(HibernateException he)
                    {he.printStackTrace();}}

        }
    
    private void displayResult(List resultList)
    {
        Vector<String> tableHeaders = new Vector<String>();
        Vector tableData = new Vector();
        tableHeaders.add("Book_Name");        
        tableHeaders.add("Author_Name");
        tableHeaders.add("Book_Status");
        for(Object o: resultList)
        {
            Links links = (Links) o;
            Vector<Object> oneRow = new Vector<Object>();
            oneRow.add(links.getBooks().getBookName());
            oneRow.add(links.getAuthors().getAuthorName());
            oneRow.add(links.getBooks().getBookStatus());
            tableData.add(oneRow);
        }
        resultTable.setModel(new DefaultTableModel(tableData, tableHeaders));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        resultTable = new javax.swing.JTable();
        queryShowAll = new javax.swing.JButton();
        addBook = new javax.swing.JButton();
        bookNameTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        deleteBook = new javax.swing.JButton();
        deleteField = new javax.swing.JTextField();
        editStatusBook = new javax.swing.JButton();
        editStatusBookStatusTextField = new javax.swing.JTextField();
        editStatusBookNameTextField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        AuthorsTextArea = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        resultTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(resultTable);

        queryShowAll.setText("Показать все книги");
        queryShowAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                queryShowAllActionPerformed(evt);
            }
        });

        addBook.setText("Добавить книгу");
        addBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBookActionPerformed(evt);
            }
        });

        jLabel1.setText("Название создаваемой книги");

        jLabel2.setText("Имя/имена авторов");

        deleteBook.setText("Удалить книгу");
        deleteBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBookActionPerformed(evt);
            }
        });

        editStatusBook.setText("Изменить статус книги");
        editStatusBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editStatusBookActionPerformed(evt);
            }
        });

        AuthorsTextArea.setColumns(20);
        AuthorsTextArea.setRows(5);
        jScrollPane2.setViewportView(AuthorsTextArea);

        jLabel3.setText("Название удаляемой книги");

        jLabel4.setText("Название книги");

        jLabel5.setText("Статус книги");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(queryShowAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(addBook)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(deleteField, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(deleteBook)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(editStatusBook)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel1)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(bookNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(editStatusBookNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(editStatusBookStatusTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(27, 27, 27)
                                        .addComponent(jLabel5)))))
                        .addGap(49, 49, 49))))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(queryShowAll)
                            .addComponent(addBook))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(deleteField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(deleteBook)
                                    .addComponent(editStatusBook)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bookNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editStatusBookNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editStatusBookStatusTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void queryShowAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_queryShowAllActionPerformed
        runShowAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_queryShowAllActionPerformed

    private void addBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBookActionPerformed
        // TODO add your handling code here:
        String BookName = bookNameTextField.getText();
        
        String [] AuthorName = AuthorsTextArea.getText().split("\n");
        addBook(BookName,AuthorName);
        runShowAll();
    }//GEN-LAST:event_addBookActionPerformed

    private void deleteBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBookActionPerformed
        
            String deletingQuery = deleteField.getText();
            deleteBook(deletingQuery);
            runShowAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteBookActionPerformed

    private void editStatusBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editStatusBookActionPerformed

        String NewBookStatus = editStatusBookStatusTextField.getText().trim();
        String BookName = editStatusBookNameTextField.getText();
        editStatus(BookName, NewBookStatus);
        runShowAll();
    }//GEN-LAST:event_editStatusBookActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(libraryClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(libraryClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(libraryClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(libraryClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new libraryClient().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea AuthorsTextArea;
    private javax.swing.JButton addBook;
    private javax.swing.JTextField bookNameTextField;
    private javax.swing.JButton deleteBook;
    private javax.swing.JTextField deleteField;
    private javax.swing.JButton editStatusBook;
    private javax.swing.JTextField editStatusBookNameTextField;
    private javax.swing.JTextField editStatusBookStatusTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton queryShowAll;
    private javax.swing.JTable resultTable;
    // End of variables declaration//GEN-END:variables
}
