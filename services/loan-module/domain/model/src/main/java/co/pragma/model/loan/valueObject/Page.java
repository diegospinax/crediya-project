package co.pragma.model.loan.valueObject;

import java.util.List;

public class Page<T> {

    private List<T> content;
    private final int pageNumber;
    private final int pageSize;
    private final long totalElements;
    private final int totalPages;
    private final boolean isFirst;
    private final boolean isLast;

    public Page(List<T> contenido, int pageNumber, int pageSize, long totalElements) {
        this.content = contenido;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;

        this.totalPages = pageSize > 0 ? (int) Math.ceil((double) totalElements / (double) pageSize) : 0;
        this.isFirst = pageNumber == 0;
        this.isLast = (pageNumber + 1) >= totalPages;
    }

    public List<T> getContent() { return content; }
    public void setContent(List<T> content) { this.content = content; }
    public int getPageNumber() { return pageNumber; }
    public int getPageSize() { return pageSize; }
    public long getTotalElements() { return totalElements; }
    public int getTotalPages() { return totalPages; }
    public boolean isFirst() { return isFirst; }
    public boolean isLast() { return isLast; }
}
