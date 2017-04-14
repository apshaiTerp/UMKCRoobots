package org.umkc.roobot.model;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author AC010168
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutboxList {

  private List<OutboxEmailHeader> outboxList;
  
  public OutboxList() {
    outboxList = new LinkedList<OutboxEmailHeader>();
  }
  
  public void addEmailHeader(OutboxEmailHeader header) {
    outboxList.add(header);
  }

  /**
   * @return the inboxList
   */
  public List<OutboxEmailHeader> getOutboxList() {
    return outboxList;
  }

  /**
   * @param inboxList the inboxList to set
   */
  public void setOutboxList(List<OutboxEmailHeader> inboxList) {
    this.outboxList = inboxList;
  }
}
