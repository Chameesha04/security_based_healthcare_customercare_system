-- Add updated_at column to tickets table
-- This script adds the updated_at column to track when replies are updated

ALTER TABLE tickets ADD COLUMN updated_at TIMESTAMP NULL;

-- Add a comment to document the column purpose
COMMENT ON COLUMN tickets.updated_at IS 'Timestamp when the reply was last updated';
