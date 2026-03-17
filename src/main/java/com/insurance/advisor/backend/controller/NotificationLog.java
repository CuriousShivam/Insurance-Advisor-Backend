//NotificationLog log = new NotificationLog();
//log.setRecipientEmail(customerEmail);
//log.setSubject("Enquiry Received");
//log.setMessage("Your request has been logged...");
//
//try {
//        // emailService.send(...) logic here
//        log.setDeliveryStatus("sent");
//    log.setSentAt(LocalDateTime.now());
//        } catch (Exception e) {
//        log.setDeliveryStatus("failed");
//    log.setErrorMessage(e.getMessage());
//        }
//        notificationLogRepository.save(log);